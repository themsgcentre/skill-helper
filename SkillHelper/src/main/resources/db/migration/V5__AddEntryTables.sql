CREATE TABLE [dbo].[Entry] (
    [Id] [bigint] IDENTITY(1,1) PRIMARY KEY,
    [Username] [nvarchar](16) NOT NULL,
    [Text] [nvarchar](max) NULL,
    [StressLevel] [int] NOT NULL,
    [Time] [DateTime] NOT NULL,
    CONSTRAINT [ENTRY_FK_USERNAME] FOREIGN KEY ([Username]) REFERENCES [User]([Username]),
    CONSTRAINT [ENTRY_STRESSLEVEL_RANGE] CHECK(StressLevel >= 0 AND StressLevel <= 100)
);