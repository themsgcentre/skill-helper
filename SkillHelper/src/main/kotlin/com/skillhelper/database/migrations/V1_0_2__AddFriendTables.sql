CREATE TABLE [dbo].[Friend] (
    [User] [nvarchar](16),
    [Friend] [nvarchar](16),
    CONSTRAINT [PK_Friend] PRIMARY KEY([User], [Friend]),
    CONSTRAINT [FK_USER] FOREIGN KEY ([User]) REFERENCES [User]([Username]),
    CONSTRAINT [FK_FRIEND] FOREIGN KEY ([Friend]) REFERENCES [User]([Username]),
    CONSTRAINT [FRIEND_DIFFER] CHECK ([User] <> [Friend])
);

CREATE TABLE [dbo].[Request] (
    [User] [nvarchar](16),
    [Request] [nvarchar](16),
    CONSTRAINT [PK_Request] PRIMARY KEY([User], [Request]),
    CONSTRAINT [FK_USER] FOREIGN KEY ([User]) REFERENCES [User]([Username]),
    CONSTRAINT [FK_REQUEST] FOREIGN KEY ([Request]) REFERENCES [User]([Username]),
    CONSTRAINT [REQUEST_DIFFER] CHECK ([User] <> [Request])
);